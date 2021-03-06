/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.jersey.server.mvc;

import org.glassfish.jersey.server.internal.LocalizationMessages;

/**
 * A viewable type referencing a template by name and a model to be passed
 * to the template. Such a type may be returned by a resource method of a
 * resource class. In this respect the template is the view and the controller
 * is the resource class in the Model View Controller pattern.
 * <p/>
 * The template name may be declared as absolute template name if the name
 * begins with a '/', otherwise the template name is declared as a relative
 * template name.
 * <p/>
 * A relative template name requires resolving to an absolute template name
 * when the viewable type is processed.
 * <br/>
 * If a resolving class is present then that class may be used to resolve the
 * relative template name.
 * <br/>
 * If a resolving class is not present and resolving class is not defined by the {@link Template} annotation
 * then the class of the last matching resource should be utilized as the resolving class.
 * <br/>
 * If there are no matching resources then an {@link org.glassfish.jersey.server.mvc.spi.ViewableContextException error}
 * will result.
 * <p/>
 * {@link org.glassfish.jersey.server.mvc.spi.ViewableContext} is responsible for
 * resolving absolute template name from relative template name.
 *
 * @author Paul Sandoz (paul.sandoz at oracle.com)
 * @author Michal Gajdos (michal.gajdos at oracle.com)
 *
 * @see Template
 * @see org.glassfish.jersey.server.mvc.spi.ViewableContext
 * @see org.glassfish.jersey.server.mvc.internal.ResolvingViewableContext
 */
public class Viewable {

    private final String templateName;

    private final Object model;

    private final Class<?> resolvingClass;

    /**
     * Construct a new viewable type with a template name.
     * <p/>
     * The model will be set to {@code null}.
     *
     * @param templateName the template name, shall not be {@code null}.
     * @throws IllegalArgumentException if the template name is {@code null}.
     */
    public Viewable(String templateName) throws IllegalArgumentException {
        this(templateName, null, null);
    }

    /**
     * Construct a new viewable type with a template name and a model.
     *
     * @param templateName the template name, shall not be {@code null}.
     * @param model the model, may be {@code null}.
     * @throws IllegalArgumentException if the template name is {@code null}.
     */
    public Viewable(String templateName, Object model) throws IllegalArgumentException {
        this(templateName, model, null);
    }

    /**
     * Construct a new viewable type with a template name, a model
     * and a resolving class.
     *
     * @param templateName the template name, shall not be {@code null}.
     * @param model the model, may be {@code null}.
     * @param resolvingClass the class to use to resolve the template name if the template is not absolute,
     * if {@code null} then the resolving class will be obtained from the last matching resource.
     * @throws IllegalArgumentException if the template name is {@code null}.
     */
    public Viewable(String templateName, Object model, Class<?> resolvingClass) throws IllegalArgumentException {
        if (templateName == null) {
            throw new IllegalArgumentException(LocalizationMessages.TEMPLATE_NAME_MUST_NOT_BE_NULL());
        }

        this.templateName = templateName;
        this.model = model;
        this.resolvingClass = resolvingClass;
    }

    /**
     * Get the template name.
     *
     * @return the template name.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Get the model.
     *
     * @return the model.
     */
    public Object getModel() {
        return model;
    }

    /**
     * Get the resolving class.
     *
     * @return the resolving class.
     */
    public Class<?> getResolvingClass() {
        return resolvingClass;
    }

    /**
     * Determines whether the template name is represented by an absolute path.
     *
     * @return {@code true} if the template name is absolute, and starts with a
     *         '/' character, {@code false} otherwise.
     */
    public boolean isTemplateNameAbsolute() {
        return templateName.length() > 0 && templateName.charAt(0) == '/';
    }
}