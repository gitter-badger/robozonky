/*
 * Copyright 2016 Lukáš Petrovický
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.triceo.robozonky.strategy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.github.triceo.robozonky.util.IoTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.Assume;
import org.junit.Test;

public class InvestmentStrategyTest {

    private static final InputStream CORRECT_STRATEGY =
            InvestmentStrategyTest.class.getResourceAsStream("strategy-correct.cfg");

    @Test
    public void loadStrategy() throws InvestmentStrategyParseException, IOException {
        final File strategyFile = IoTestUtil.streamToFile(InvestmentStrategyTest.CORRECT_STRATEGY);
        final Optional<InvestmentStrategy> inv = InvestmentStrategy.load(strategyFile);
        Assertions.assertThat(inv).isEmpty(); // no strategies are on the classpath
    }

    @Test(expected = NullPointerException.class)
    public void nullStrategy() throws InvestmentStrategyParseException {
        InvestmentStrategyLoader.load(null);
    }

    @Test(expected = InvestmentStrategyParseException.class)
    public void nonExistentStrategy() throws InvestmentStrategyParseException, IOException {
        final File strategy = File.createTempFile("robozonky-", ".cfg");
        Assume.assumeTrue(strategy.delete());
        InvestmentStrategyLoader.load(strategy);
    }
}
