'use client';

import React, { useState, useRef, useEffect } from 'react';

function AnimatedText({ originalText, animar, tag: Tag = 'h1', onMouseEnter, onMouseLeave }) {
  const [textoAnimado, setTextoAnimado] = useState(originalText);
  const intervaloRef = useRef(null);

  const gerarTextoAleatorio = () => {
    let textoAleatorio = '';
    for (let i = 0; i < originalText.length; i++) {
      const caracteres = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      textoAleatorio += caracteres.charAt(Math.floor(Math.random() * caracteres.length));
    }
    return textoAleatorio;
  };

  useEffect(() => {
    if (animar) {
      intervaloRef.current = setInterval(() => {
        let textoAleatorioFormatado = gerarTextoAleatorio();

        if (Tag === 'h1') {
          textoAleatorioFormatado = textoAleatorioFormatado.toUpperCase();
        } else if (Tag === 'p') {
          textoAleatorioFormatado = textoAleatorioFormatado.toLowerCase();
        }

        setTextoAnimado(textoAleatorioFormatado);
      }, 100);
    } else {
      clearInterval(intervaloRef.current);
      setTextoAnimado(originalText);
    }

    return () => clearInterval(intervaloRef.current);
  }, [animar, originalText, Tag]);

  return (
    <Tag onMouseEnter={onMouseEnter} onMouseLeave={onMouseLeave}>
      {textoAnimado}
    </Tag>
  );
}

export default AnimatedText;