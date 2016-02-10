# Change Log
All notable changes to this project will be documented in this file.

## [Unreleased]

### Breaking
- Renamed SwaggerMerger to SwaggerMerge.
- SwaggerMerge returns a MergeResult instead of Swagger. SwaggerMergeException is not thrown anymore. Instead check the result for conflicts.
```java
Swagger swagger = swaggerMerge.merge(first, second);
```

```java
Swagger swagger = swaggerMerge.merge(first, second).swagger();
```

### Added
- nothing yet

### Changed
- nothing yet

### Deprecated
- nothing yet

### Removed
- nothing yet

### Fixed
- nothing yet

## [0.1.0] - 2016-01-31
Initial release
