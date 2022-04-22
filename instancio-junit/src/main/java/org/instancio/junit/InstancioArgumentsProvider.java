/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.instancio.junit;

import org.instancio.Instancio;
import org.instancio.internal.ThreadLocalRandomProvider;
import org.instancio.internal.ThreadLocalSettings;
import org.instancio.internal.random.RandomProvider;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A provider that generates arguments for {@link InstancioSource}.
 */
public class InstancioArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<InstancioSource> {

    private InstancioSource instancioSource;

    @Override
    public void accept(final InstancioSource instancioSource) {
        this.instancioSource = instancioSource;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        final ThreadLocalRandomProvider threadLocalRandomProvider = ThreadLocalRandomProvider.getInstance();
        final ThreadLocalSettings threadLocalSettings = ThreadLocalSettings.getInstance();

        ExtensionSupport.processWithSettingsAnnotation(context, threadLocalSettings);
        ExtensionSupport.processSeedAnnotation(context, threadLocalRandomProvider);

        final RandomProvider randomProvider = threadLocalRandomProvider.get();
        final Settings settings = threadLocalSettings.get();

        final Object[] args = Arrays.stream(instancioSource.value())
                .map(it -> Instancio.of(it)
                        .withSettings(settings)
                        .withSeed(randomProvider.getSeed())
                        .create())
                .toArray();

        return Stream.of(Arguments.of(args));
    }
}