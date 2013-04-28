/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.pax.exam.container.karaf;

import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.configureConsole;
import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.logLevel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.maven;

import javax.inject.Inject;

import org.apache.karaf.tooling.exam.options.LogLevelOption.LogLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

@RunWith(PaxExam.class)
public class KarafTestContainerTest {

    @Inject
    private BundleContext bc;

    @Configuration
    public Option[] config() {
        return new Option[] {
            karafDistributionConfiguration()
                .frameworkUrl(maven("org.apache.karaf", "apache-karaf", "2.2.9").type("zip"))
                .karafVersion("2.2.9").useDeployFolder(false),
            configureConsole().ignoreLocalConsole().startRemoteShell(), logLevel(LogLevel.INFO) };
    }

    @Test
    public void checkKarafSystemService() throws Exception {
        assertThat(bc, is(notNullValue()));
        ServiceReference serviceRef = bc
            .getServiceReference("org.apache.karaf.features.FeaturesService");
        Object service = bc.getService(serviceRef);
        assertThat(service, is(notNullValue()));
    }
}
