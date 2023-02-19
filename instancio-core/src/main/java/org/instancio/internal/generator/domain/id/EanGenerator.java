/*
 * Copyright 2022-2023 the original author or authors.
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
package org.instancio.internal.generator.domain.id;

import org.instancio.Random;
import org.instancio.generator.GeneratorContext;
import org.instancio.internal.generator.AbstractGenerator;

public class EanGenerator extends AbstractGenerator<String> {

    private EanType type = EanType.EAN13;

    public EanGenerator(final GeneratorContext context) {
        super(context);
    }

    @Override
    public String apiMethod() {
        return null;
    }

    public EanGenerator ean8() {
        type = EanType.EAN8;
        return this;
    }

    @Override
    public String generate(final Random random) {
        final String withoutCheckDigit = random.digits(type.length - 1);
        final int checkDigit = getCheckDigit(withoutCheckDigit, type);
        return withoutCheckDigit + checkDigit;
    }

    private static int getCheckDigit(final String s, final EanType type) {
        int even = 0;
        int odd = 0;

        for (int i = 0; i < s.length(); i++) {
            final int idx = i + 1;
            final int d = s.charAt(i) - '0';
            if (idx % 2 == 0) {
                even += d;
            } else {
                odd += d;
            }
        }

        final int sum = type == EanType.EAN8
                ? 3 * odd + even
                : 3 * even + odd;

        return (10 - (sum % 10)) % 10;
    }

    private enum EanType {
        EAN8(8),
        EAN13(13);

        private final int length;

        EanType(final int length) {
            this.length = length;
        }
    }
}

