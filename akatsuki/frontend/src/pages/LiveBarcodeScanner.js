import React, { useEffect, useRef, useState } from 'react';
import Quagga from 'quagga';  // npm install quagga

const LiveBarcodeScanner = ({ onDetected }) => {
  const videoRef = useRef(null);
  const [scanning, setScanning] = useState(false);

  useEffect(() => {
    if (!scanning) return;
    Quagga.init({
      inputStream: {
        name: 'Live',
        type: 'LiveStream',
        target: videoRef.current,
        constraints: { facingMode: 'environment' }
      },
      decoder: { readers: ['ean_reader', 'code_128_reader'] }
    }, err => {
      if (err) { console.error(err); return; }
      Quagga.start();
    });
    Quagga.onDetected(result => {
      const code = result.codeResult.code;
      onDetected(code);
      Quagga.stop();
      setScanning(false);
    });
    return () => {
      Quagga.offDetected();
      Quagga.stop();
    };
  }, [scanning, onDetected]);

  return (
    <div>
      <div ref={videoRef} style={{ width: '100%' }} />
      {!scanning && (
        <button onClick={() => setScanning(true)}>
          Start Live Scan
        </button>
      )}
    </div>
  );
};

export default LiveBarcodeScanner;
