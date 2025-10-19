<!--
Sync Impact Report
- Version change: 1.0.0 → 1.1.0
- Modified principles: 
  - IV. Reserved: Testing & Contracts (placeholder) → IV. Testing & Contracts
  - V. Reserved: Observability & Versioning (placeholder) → V. Observability & Versioning
- Added sections: None (expanded existing principles)
- Removed sections: None
- Templates requiring updates:
  - ✅ .specify/templates/plan-template.md (Constitution Check remains generic and compatible)
  - ✅ .specify/templates/spec-template.md (no constitution-coupled constraints)
  - ✅ .specify/templates/tasks-template.md (organization guidance unchanged)
  - ✅ .specify/templates/agent-file-template.md (no outdated agent-specific references)
- Deferred TODOs: None
-->

# EliosUserService Constitution

## Core Principles

### I. Layered Architecture Boundaries
- Controllers MUST only orchestrate requests and map to application use cases.
- Application layer MUST expose use cases via ports/interfaces; no framework code.
- Domain layer contains entities, value objects, and policies; it MUST be
  framework-agnostic and have zero outward dependencies.
- Infra layer provides adapters (persistence, messaging, HTTP, etc.) that depend
  inward on application ports; no domain or application logic lives here.
- Cross-layer calls MUST only depend inward (controller → application → domain;
  infra → application/domain via ports). No lateral or outward leaks.

Rationale: Enforces clear separation of concerns, enables testability and swap of
infrastructure without changing core business logic.

### II. Dependency Rules and Wiring
- Use constructor injection for all dependencies. No field or setter injection.
- Define ports/interfaces in the application layer; implement them in infra.
- Use `@Configuration` classes (or equivalent) for composition root wiring.
- Domain MUST not depend on any frameworks, annotations, or runtime containers.

Rationale: Inversion of control preserves domain purity and enables isolated tests
and alternative adapter implementations.

### III. Modularity and Shared Kernels
- Each package/module has exactly one responsibility; avoid god-packages.
- Shared kernels (e.g., common exceptions, DTO mappers) MUST reside in clearly
  named packages and avoid pulling in infra concerns.
- Mappers and DTOs belong to application unless strictly persistence-specific
  (which belong to infra adapters).

Rationale: Keeps coupling intentional and reuse safe without contaminating boundaries.

### IV. Testing & Contracts
- Unit tests MUST cover domain logic and application use cases in isolation.
- Integration tests MUST verify adapter implementations against application ports.
- Contract tests MUST validate API endpoints and message schemas for external
  consumers.
- Test doubles MUST implement application ports, not concrete infra classes.
- Domain tests MUST run without any framework dependencies or external resources.

Rationale: Ensures business logic correctness and adapter compliance while
maintaining fast, reliable test suites that don't break with infrastructure changes.

### V. Observability & Versioning
- Structured logging MUST be used throughout with consistent field names and
  correlation IDs for request tracing.
- All external APIs MUST follow semantic versioning (MAJOR.MINOR.PATCH).
- Breaking changes MUST increment MAJOR version and include migration guides.
- Health checks MUST be implemented for all external dependencies.
- Metrics MUST be exposed for business-critical operations and system health.

Rationale: Enables effective debugging, monitoring, and safe evolution of APIs
while maintaining backward compatibility for consumers.

## Implementation Workflow

- New features start from application use cases; define ports first, then adapters.
- Persistence and messaging live in infra adapters that implement application ports.
- Controllers map HTTP/transport contracts to use cases and DTOs.
- Use constructor injection throughout and centralize wiring in configuration.
- No direct controller → infra or controller → domain dependencies.

## Quality Gates

- PRs MUST include an architectural check verifying inward-only dependencies.
- Domain classes MUST compile with no framework imports.
- Infra adapters MUST not contain business rules.
- DTO mappers MUST not reach into persistence models outside infra.
- Any exception types shared MUST live in a common, framework-agnostic package.
- New features MUST include unit tests for domain logic and integration tests for
  adapters.
- API changes MUST include contract tests and version bump if breaking.
- All external dependencies MUST have health checks implemented.

## Governance

- This constitution supersedes ad-hoc practices regarding architecture and wiring.
- Amendments: Propose via PR updating this file. Include migration notes if any
  changes affect structure or dependencies. Approval by code owners required.
- Versioning: Semantic versioning for this document
  - MAJOR: Backward-incompatible governance changes or removals
  - MINOR: New principles/sections or material expansions
  - PATCH: Clarifications and non-semantic refinements
- Compliance: Reviewers MUST enforce these rules. Violations require an explicit
  justification section in the PR and a follow-up task to remove the deviation.

**Version**: 1.1.0 | **Ratified**: 2025-10-19 | **Last Amended**: 2025-10-19
