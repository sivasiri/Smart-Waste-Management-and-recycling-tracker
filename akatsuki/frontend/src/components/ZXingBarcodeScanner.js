import React, { useEffect, useRef } from 'react';
import { BrowserMultiFormatReader } from '@zxing/browser';

export default function ZXingBarcodeScanner({ onDetected }) {
  const videoRef = useRef(null);

  useEffect(() => {
    const codeReader = new BrowserMultiFormatReader();

    codeReader
      .decodeFromVideoDevice(
        undefined,       // default camera
        videoRef.current,
        (result, error) => {
          if (result) {
            const code = result.getText();
            onDetected(code);

            // STOP continuous scanning immediately
            if (typeof codeReader.stopContinuousDecode === 'function') {
              codeReader.stopContinuousDecode();
            }
          }
          // ignore “no code found in this frame” errors
        }
      )
      .catch(err => console.error('ZXing init error:', err));

    return () => {
      // On unmount, also stop continuous decode
      if (typeof codeReader.stopContinuousDecode === 'function') {
        codeReader.stopContinuousDecode();
      }
    };
  }, [onDetected]);

  return (
    <div style={{ textAlign: 'center' }}>
      <video
        ref={videoRef}
        style={{
          width: '100%',
          maxWidth: '400px',
          height: '300px',
          margin: '0 auto 10px',
          background: '#000',
        }}
        muted
        playsInline
      />
      <p>Scanning… point your camera at a barcode</p>
    </div>
  );
}
