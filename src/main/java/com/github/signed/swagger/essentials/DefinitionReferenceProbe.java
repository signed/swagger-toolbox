package com.github.signed.swagger.essentials;

import com.github.signed.swagger.trim.DefinitionReference;
import io.swagger.models.Swagger;
import java.util.List;

public interface DefinitionReferenceProbe {
    List<DefinitionReference> retrieveReferencesIn(Swagger swagger);
}
