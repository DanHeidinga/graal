/*
 * Copyright (c) 2019, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.svm.thirdparty.jline;

import java.util.function.BooleanSupplier;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature;

import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

@AutomaticFeature
final class JLine3Feature implements Feature {
    @Override
    public boolean isInConfiguration(IsInConfigurationAccess access) {
        return access.findClassByName("org.jline.terminal.impl.jna.JnaSupportImpl") != null;
    }

    static final class IsEnabled implements BooleanSupplier {
        @Override
        public boolean getAsBoolean() {
            return ImageSingletons.contains(JLine3Feature.class);
        }
    }
}

@TargetClass(className = "org.jline.terminal.impl.jna.JnaSupportImpl", onlyWith = JLine3Feature.IsEnabled.class)
final class Target_org_jline_terminal_impl_jna_JnaSupportImpl_open {
    @Substitute
    public Object open() {
        throw new RuntimeException();
    }
}

@TargetClass(className = "org.mozilla.universalchardet.UniversalDetector", onlyWith = JLine3Feature.IsEnabled.class)
final class Target_org_mozilla_universalchardet_UniversalDetector {
    @Substitute
    Target_org_mozilla_universalchardet_UniversalDetector() {
        throw new RuntimeException();
    }
}
