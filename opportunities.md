SwaggerMerge:
- Do not communicate merge conflicts with exceptions

SwaggerTrim:
- OperationMother to ensure all the necessary fields are set for validation

SwaggerSort:
- Sort elements in a reproducible manor if they are not already sorted by json annotations
  - paths
  - definitions
  - parameters
  - ...

SwaggerValidate:
- check for ambiguity in path definitions
  - /{variable} and /constant

SwaggerCompare
- Decide if two api definitions are the same and report differences
- define what 'the same' means
- https://github.com/skyscreamer/JSONassert (check for comparing json)

- there is a java script tool out there that performs some semantic checks on swagger files 
  https://github.com/apigee-127/swagger-tools/blob/master/docs/Swagger_Validation.md

# interesting reads #
- json ref   http://azimi.me/2015/07/16/split-swagger-into-smaller-files.html