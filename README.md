# Kanban Board - N-tier #

Simple kanban board using N-tier (layered) architecute.
Will be converted to screaming architecture, clean architecute.

## Backend structure ##

```
backend/
└── src
    ├── main
    │   ├── java
    │   │   └── com.keresman.kanbanboard
    │   │       ├── config
    │   │       ├── controller
    │   │       ├── dto
    │   │       ├── exception
    │   │       │   ├── functional
    │   │       │   ├── handler
    │   │       │   └── model
    │   │       ├── mapping
    │   │       ├── model
    │   │       ├── payload
    │   │       ├── repository
    │   │       ├── s3
    │   │       │   └── impl
    │   │       ├── scheduler
    │   │       │   └── impl
    │   │       ├── security
    │   │       ├── service
    │   │       │   └── impl
    │   │       └── utility
    │   └── resources
    └── test
        └── java
            └── com.keresman.kanbanboard

```

## Frontend structure - WIP ##

