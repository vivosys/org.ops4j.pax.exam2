/*
 * Copyright (C) 2011 Harald Wellmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.regression.nat.felix.options;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.CoreOptions.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.ops4j.pax.exam.util.PathUtils;
import org.ops4j.pax.exam.util.ServiceLookup;
import org.osgi.framework.BundleContext;
import org.osgi.service.startlevel.StartLevel;

@RunWith( JUnit4TestRunner.class )
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class FrameworkPropertyTest
{

    @Configuration
    public Option[] config()
    {
        return options(
            frameworkProperty( "felix.startlevel.bundle" ).value( "6" ),
            junitBundles() 
            );
    }

    @Test
    public void startLevel( BundleContext bc )
    {
        StartLevel startLevel = ServiceLookup.getService( bc, StartLevel.class );
        assertThat( startLevel, is( notNullValue() ) );
        assertThat(startLevel.getInitialBundleStartLevel(), is(equalTo(6)));
    }
}
