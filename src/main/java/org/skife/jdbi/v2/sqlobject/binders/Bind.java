/*
 * Copyright 2004 - 2011 Brian McCallister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.skife.jdbi.v2.sqlobject.binders;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@BindingAnnotation(Bind.Factory.class)
public @interface Bind
{
    String value() default "it";

    Class<? extends Binder> binder() default DefaultObjectBinder.class;

    static class Factory implements BinderFactory
    {
        public Binder build(Annotation annotation)
        {
            Bind bind = (Bind) annotation;
            try {
                return bind.binder().newInstance();
            }
            catch (Exception e) {
                throw new IllegalStateException("unable to instantiate specified binder", e);
            }
        }
    }

}