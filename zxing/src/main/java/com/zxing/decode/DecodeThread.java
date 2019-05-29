/*
 * Copyright (C) 2008 ZXing authors
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

package com.zxing.decode;

import android.os.Handler;
import android.os.Looper;

import com.google.zxing.DecodeHintType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.zxing.ScanManager;


public class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";

	public static final int BARCODE_MODE = 0X100;
	public static final int QRCODE_MODE = 0X200;
	public static final int ALL_MODE = 0X300;

	final ScanManager scanManager;
	static Map<DecodeHintType, Object> hints;
	Handler handler;
	final CountDownLatch handlerInitLatch;

	public DecodeThread(ScanManager scanManager, int decodeMode) {

		this.scanManager = scanManager;
		handlerInitLatch = new CountDownLatch(1);

		hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

		Map<DecodeHintType,Object> hints = new HashMap<>();
		//添加可以解析的编码类型
		hints.put(DecodeHintType.POSSIBLE_FORMATS, DecodeFormatManager.getQrCodeFormats());

	}

	public Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(scanManager, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

	public static Map<DecodeHintType, Object> getHints() {
		return hints;
	}
}
