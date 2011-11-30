/*
 * Copyright 2011 John Ahlroos
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
package com.vaadin.incubator.dragdroplayouts.events;

import com.vaadin.event.dd.acceptcriteria.TargetDetailIs;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;

/**
 * A client side criterion for determining the horizontal location
 */
@SuppressWarnings("serial")
public final class HorizontalLocationIs extends TargetDetailIs {
    public static final HorizontalLocationIs LEFT = new HorizontalLocationIs(
            HorizontalDropLocation.LEFT);
    public static final HorizontalLocationIs CENTER = new HorizontalLocationIs(
            HorizontalDropLocation.CENTER);
    public static final HorizontalLocationIs RIGHT = new HorizontalLocationIs(
            HorizontalDropLocation.RIGHT);

    private HorizontalLocationIs(HorizontalDropLocation l) {
        super("hdetail", l.name());
    }
}
