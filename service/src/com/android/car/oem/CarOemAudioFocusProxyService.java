/*
 * Copyright (C) 2022 The Android Open Source Project
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
package com.android.car.oem;

import android.car.builtin.util.Slogf;
import android.car.oem.IOemCarAudioFocusService;
import android.media.AudioFocusInfo;
import android.os.RemoteException;

import com.android.car.CarLog;

import java.util.List;

// TODO(b/239605560): Add check for circular call.
/**
 * Provides functionality of the OEM Audio Focus Service.
 */
public final class CarOemAudioFocusProxyService {

    private static final String TAG = CarLog.tagFor(CarOemAudioFocusProxyService.class);

    private final CarOemProxyServiceHelper mHelper;
    private final IOemCarAudioFocusService mOemCarAudioFocusService;

    public CarOemAudioFocusProxyService(CarOemProxyServiceHelper helper,
            IOemCarAudioFocusService oemAudioFocusService) {
        mHelper = helper;
        mOemCarAudioFocusService = oemAudioFocusService;
    }

    /**
     * Updates audio focus changes.
     */
    public void audioFocusChanged(List<AudioFocusInfo> currentFocusHolders,
            List<AudioFocusInfo> currentFocusLosers, int zoneId) {
        mHelper.doBinderOneWayCall(() -> {
            try {
                mOemCarAudioFocusService
                        .audioFocusChanged(currentFocusHolders, currentFocusLosers, zoneId);
            } catch (RemoteException e) {
                Slogf.e(TAG, e,
                        "audioFocusChanged call received RemoteException- currentFocusHolders:%s, "
                        + "currentFocusLosers:%s, ZoneId: %s, , calling to crash CarService",
                        currentFocusHolders, currentFocusLosers, zoneId);
                mHelper.crashCarService("Remote Exception");
            }
        });
    }
}