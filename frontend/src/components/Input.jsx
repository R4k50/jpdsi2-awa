import './styles/Input.scss';
import { forwardRef, useEffect, useId, useRef, useState } from 'react';
import { animated, useSpring } from 'react-spring';

const Input = forwardRef((props, ref) => {
  const {
    label,
    onFocus,
    onBlur,
    onInput,
    errors
  } = props;

  const id = useId();
  const inputRef = useRef();
  const labelRef = useRef();
  const [isFocused, setIsFocused] = useState(false);
  const [value, setValue] = useState(props?.value ?? '');

  useEffect(() => {
    ref = inputRef;
  }, [inputRef]);

  useEffect(() => {
    setValue(props?.value ?? '');

    if (props?.value)
      setIsFocused(() => true);
  }, [props.value]);

  const handler = (handlerProp, handlerEvent, event) => {
    handlerEvent(event);

    if (handlerProp)
      handlerProp(event);
  }

  const handleInput = e => {
    setValue(() => e.target.value);
  }

  const handleFocus = () => {
    setIsFocused(() => true);
  }

  const handleBlur = () => {
    if (!value)
      setIsFocused(() => false);
  }

  const handleClick = () => {
    inputRef.current.focus();
  }


  const labelAnimation = useSpring({
    from: {
      cursor: 'text',
      color: '#b3b3b3',
      transformOrigin: 'center left',
      y: "0rem",
      x: "0rem",
      scale: 1
    },
    to: {
      color: isFocused ? (errors ? '#FF7171' : '#FE7223') : '#b3b3b3',
      y: isFocused ? "-0.9rem" : "0rem",
      x: isFocused ? "0.25rem" : "0rem",
      scale: isFocused ? .75 : 1
    },
    config: {
      duration: 150
    }
  });

  const bgAnimation = useSpring({
    from: {
      cursor: 'text',
      transformOrigin: 'center left',
      scaleX: 0,
      translateY: "-100%",
    },
    to: {
      scaleX: isFocused ? .75 : 0
    },
    config: { duration: 50 }
  });

  const outlineAnimation = useSpring({
    from: { outlineColor: '#b3b3b3' },
    to: { outlineColor: isFocused ? (errors ? '#FF7171' : '#FE7223') : '#b3b3b3' },
    config: {
      duration: 150
    }
  });

  return (
    <div className="Input">
      <animated.label htmlFor={`${label}_id`} style={labelAnimation} onClick={handleClick} ref={labelRef}>
        {label}
      </animated.label>
      <animated.div className="label-bg" style={bgAnimation} onClick={handleClick}>
        <span>{label}</span>
      </animated.div>
      <animated.input
        {...props}
        id={`${id} ${props?.id}`}
        style={{...props?.style, ...outlineAnimation}}
        placeholder={label || " "}
        value={value}
        ref={inputRef}
        onInput={e => handler(onInput, handleInput, e)}
        onFocus={e => handler(onFocus, handleFocus, e)}
        onBlur={e => handler(onBlur, handleBlur, e)}
      />
    </div>
  );
});

export default Input;