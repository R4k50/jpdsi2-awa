import './styles/Errors.scss';
import { useEffect, useState } from 'react';
import { animated, useTransition } from 'react-spring';

const Errors = ({errors}) => {
  const [errorsState, setErrorsState] = useState(errors);

  useEffect(() => {
    setErrorsState(() => errors);
  }, [errors])


  const transition = useTransition(errorsState, {
    from: {
      opacity: 0,
      x: 0,
      y: 100
    },
    enter: {
      opacity: 1,
      x: 0,
      y: 0
    },
    leave: {
      opacity: 0,
      y: 0,
      x: 100
    },
    onRest: async () => {
      await errorsState && setTimeout(() => {
        setErrorsState(() => errorsState.filter(() => false));
      }, 5000);
    },
    trail: 100,
  });

  return (
    <animated.div className='Errors'>
      {errorsState && <div>
          {transition((style, error, key) => (
            error && <animated.li className="error" key={key} style={style}>{error}</animated.li>
          ))}
        </div>
      }
    </animated.div>
  );
}

export default Errors;