import { forwardRef, useState } from 'react';
import { animated, useSpring } from 'react-spring';
import './styles/Hamburger.scss';

const Hamburger = forwardRef(({menuVisibility, onClick, className}, ref) => {
  const [duration] = useState(150);

  const mainAnimation = useSpring({
    from: {
      scale: 1,
      rotate: '0deg'
    },
    to: {
      scale: menuVisibility ? 1.2 : 1,
      rotate: menuVisibility ? '45deg': '0deg'
    },
    config: {
      duration
    }
  });

  const topLineAnimation = useSpring({
    from: {
      y: '0rem',
      rotate: '0deg',
      scale: 1
    },
    to: {
      y: menuVisibility ? '0.42rem': '0rem',
      rotate: menuVisibility ? '90deg': '0deg',
     },
     config: {
      duration
    }
  });

  const bottomLineAnimation = useSpring({
    from: {
      y: '0rem',
      scale: 1
    },
    to: {
      y: menuVisibility ? '-0.43rem': '0rem',
    },
    config: {
      duration
    }
  });

  const handleClick = e => {
    e.stopPropagation();

    if (onClick)
      onClick();
  }

  return (
    <animated.div onClick={handleClick} style={mainAnimation} ref={ref} className={`Hamburger ${className}`}>
      <animated.div style={topLineAnimation} className='line'></animated.div>
      <div className='line'></div>
      <animated.div style={bottomLineAnimation} className='line'></animated.div>
    </animated.div>
  );
});

export default Hamburger;