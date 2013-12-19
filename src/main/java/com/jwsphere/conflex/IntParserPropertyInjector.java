// Copyright 2013 Jonathan Wonders
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.jwsphere.conflex;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

public class IntParserPropertyInjector implements ConflexPropertyInjector {

	private static final Logger LOG = 
			Logger.getLogger(IntParserPropertyInjector.class);
	
	private int fallback;

	public IntParserPropertyInjector() {
		this.fallback = 0;
	}

	public IntParserPropertyInjector(int fallback) {
		this.fallback = fallback;
	}

	public void inject(Object target, Field field, String value, String defaultValue) {
		try {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				int i = Integer.parseInt(value);
				field.setInt(target, i);
			} catch (NumberFormatException e) {
				try {
					int i = Integer.parseInt(defaultValue);
					field.setInt(target, i);
				} catch (NumberFormatException e2) {
					field.setInt(target, fallback);
				}
			}
		} catch (IllegalAccessException e) {
			LOG.warn("Unable to set field " + field.getName(), e);
		} catch (SecurityException e) {
			LOG.warn("Security exception setting field", e);
		}
	}

}