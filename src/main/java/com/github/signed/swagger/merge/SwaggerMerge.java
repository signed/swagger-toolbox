package com.github.signed.swagger.merge;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.signed.swagger.essentials.SwaggerStreams;
import com.github.signed.swagger.trim.ToolboxPath;
import com.google.common.collect.Maps;
import io.swagger.models.Model;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.util.Json;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class SwaggerMerge {

    private final SwaggerStreams swaggerStreams = new SwaggerStreams();

    public MergeResult merge(Swagger one, Swagger two) {
        try {
            Map<String, Path> mergedPaths = mergePathDefinitions(one, two);
            Map<String, Model> mergedDefinitions = mergedModelDefinitions(one, two);
            List<Tag> mergedTagDefinitions = mergedTagDefinitions(one, two);

            Swagger swagger = new Swagger();
            swagger.setPaths(mergedPaths.isEmpty() ? null : mergedPaths);
            swagger.setDefinitions(mergedDefinitions.isEmpty() ? null : mergedDefinitions);
            swagger.setTags(mergedTagDefinitions.isEmpty() ? null : mergedTagDefinitions);

            return MergeResult.success(swagger);
        } catch (SwaggerMergeException exception) {
            return MergeResult.failed(null, (mergeFailureCause) -> mergeFailureCause.conflict(exception));
        }
    }

    private Map<String, Path> mergePathDefinitions(Swagger one, Swagger two) {
        List<Pair<String, String>> conflictingPathDefinitions = swaggerStreams.toolboxPathStream(one)
            .filter(p1 -> swaggerStreams.toolboxPathStream(two).anyMatch(path -> path.referenceSameResource(p1)))
            .map(serializeBothModelElementsToJson2(one, two, (swagger, path) -> swagger.getPaths().get(path)))
            .filter(thoseWhoAreNotIdentical())
            .collect(toList());


        if (!conflictingPathDefinitions.isEmpty()) {
            throw new SwaggerMergeException("conflicting path definitions");
        }

        LinkedHashMap<String, Path> mergedPaths = new LinkedHashMap<>();
        mergedPaths.putAll(swaggerStreams.paths(one));
        mergedPaths.putAll(swaggerStreams.paths(two));
        return mergedPaths;
    }

    private Map<String, Model> mergedModelDefinitions(Swagger one, Swagger two) {
        List<Pair<String, String>> conflictingModelDefinition = swaggerStreams.definitions(one).keySet().stream().
            filter(definitionsContainedInBooth(two))
            .map(serializeBothModelElementsToJson(one, two, (swagger, definitionIdentifier) -> swagger.getDefinitions().get(definitionIdentifier)))
            .filter(thoseWhoAreNotIdentical())
            .collect(toList());

        if (!conflictingModelDefinition.isEmpty()) {
            throw new SwaggerMergeException("conflicting model definitions");
        }

        LinkedHashMap<String, Model> mergedDefinitions = new LinkedHashMap<>();
        mergedDefinitions.putAll(swaggerStreams.definitions(one));
        mergedDefinitions.putAll(swaggerStreams.definitions(two));
        return mergedDefinitions;
    }

    private List<Tag> mergedTagDefinitions(Swagger one, Swagger two) {
        Map<String, Tag> firstTagByName = swaggerStreams.tagsStream(one).collect(Collectors.toMap(Tag::getName, tag -> tag));
        Map<String, Tag> secondTagByName = swaggerStreams.tagsStream(two).collect(Collectors.toMap(Tag::getName, tag -> tag));
        List<Pair<String, String>> conflictingTagDefinitions = firstTagByName.keySet().stream().filter(secondTagByName::containsKey)
            .map(serializeBothModelElementsToJson(one, two, tagWithName()))
            .filter(thoseWhoAreNotIdentical())
            .collect(toList());

        if (!conflictingTagDefinitions.isEmpty()) {
            throw new SwaggerMergeException("conflicting tag definitions");
        }

        Map<String, Tag> mergedTagDefinitions = Maps.newLinkedHashMap();
        swaggerStreams.tagsStream(one).forEach(tag -> mergedTagDefinitions.put(tag.getName(), tag));
        swaggerStreams.tagsStream(two).forEach(tag -> mergedTagDefinitions.put(tag.getName(), tag));

        return mergedTagDefinitions.values().stream().collect(toList());
    }

    private BiFunction<Swagger, String, Object> tagWithName() {
        return (swagger, s) -> swagger.getTags().stream()
            .filter(tag -> s.equals(tag.getName()))
            .findFirst().get();
    }

    private Predicate<Pair<String, String>> thoseWhoAreNotIdentical() {
        return jsonPair -> !jsonPair.getLeft().equals(jsonPair.getRight());
    }

    private Function<ToolboxPath, Pair<String, String>> serializeBothModelElementsToJson2(Swagger one, Swagger two, BiFunction<Swagger, String, Object> extractor) {
        return toolboxPath -> serializeBothModelElementsToJson(one, two, extractor).apply(toolboxPath.asString());
    }

    private Function<String, Pair<String, String>> serializeBothModelElementsToJson(Swagger one, Swagger two, BiFunction<Swagger, String, Object> extractor) {
        return stringIdentifier -> {
            Object _1st = extractor.apply(one, stringIdentifier);
            Object _2nd = extractor.apply(two, stringIdentifier);
            try {
                return Pair.of(Json.mapper().writeValueAsString(_1st), Json.mapper().writeValueAsString(_2nd));
            } catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
        };
    }

    private Predicate<String> definitionsContainedInBooth(Swagger two) {
        return modelDefinitionIdentifier -> swaggerStreams.definitions(two).containsKey(modelDefinitionIdentifier);
    }
}
