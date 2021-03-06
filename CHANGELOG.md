# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.3.0] - 2021-11-07

### Added
- new methods to `UCheck` interface:
  - `setItemTags` and `getItemTags`
  - `getFilteredTagsItemsInStores` with or without `Coordinates customerCoordinates` argument
- new `UCheck` interface methods implementation to `UCheckRamImpl` class
- model class: `Tag`
- tests for new `UCheck` interface methods
- `testReturnEmptyList` test to `TestGetFilteredItemInStores.java`

### Changed
- split `models` package classes and interfaces into subpackages:
  - `base.item`: `Item`, `Measure`, `Tag`
  - `base`: `Check`, `Coordinates`, `ItemInStore`, `Store`
  - `cart`: `CartInStore`, `ItemInCart`
  - `filter`: `Filter`
  - `rating`: `Rating`, `Review`
  - `sort`: `Sortable`, `SortParameter`, `SortRule`

## [0.2.0] - 2021-11-07

### Added
- `getFilteredCartInStores` with or without `Coordinates customerCoordinates` argument to `UCheck` interface
- `getFilteredCartInStores` implementation to `UCheckRamImpl` class
- `getCartInStorePredicate` method to `Filter` class
- model classes: `ItemInCart`, `CartInStore`
- interface `Sortable`
- tests for `getFilteredCartInStores`

### Changed
- made `SortRule` a generic class with `Sortable` type parameter
- `ItemInStore` now implements `Sortable` interface

### Fixed
- `hashCode` in `ItemInStore` class

## [0.1.0] - 2021-11-06

### Added
- UCheck interface with methods:
  - `addCheck`
  - `removeOldChecks`
  - `getStoreRating`
  - `getFilteredItemInStores` with or without `Coordinates customerCoordinates` argument
- `UCheckException` as custom UCheck exception
- `UCheckRamImpl` &mdash; `UCheck` interface implementation, which stores information in RAM
- model classes: `Check`, `Coordinates`, `Filter`, `Item`, `ItemInStore`, `Measure`, `Rating`, `Review`, `SortParameter`, `SortRule`, `Store`
- tests for `UCheckRamImpl`

<!-- [unreleased]: https://github.com/DLochmelis33/UCheck/pull/8 -->
[0.3.0]: https://github.com/DLochmelis33/UCheck/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/DLochmelis33/UCheck/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/DLochmelis33/UCheck/releases/tag/v0.1.0
