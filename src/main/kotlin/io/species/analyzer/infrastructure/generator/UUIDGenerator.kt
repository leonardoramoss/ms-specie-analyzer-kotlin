package io.species.analyzer.infrastructure.generator

import java.util.UUID

interface UUIDGenerator<in T> : Generator<T, UUID>
