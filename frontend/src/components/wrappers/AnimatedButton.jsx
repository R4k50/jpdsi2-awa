import { forwardRef, useState } from 'react';
import { animated, useSpring } from 'react-spring';


const AnimatedButton = forwardRef((props, ref) => {
  const [open, toggle] = useState(false);

  const animation = useSpring({
    from: { scale: 1 },
    to: { scale: open ? 1.1 : 1 },
    onResolve: () => { toggle(() => false) },
    config: {
      duration: 150
    }
  });

  const startAnimation = () => toggle(() => true);

  const handleClick = e => {
    e.stopPropagation();

    startAnimation();

    if (props.onClick)
      props.onClick();
  }

  return (
    <animated.button
      {...props}
      ref={ref}
      style={{...props?.style, ...animation}}
      onClick={handleClick}
    >
      {props.children}
    </animated.button>
  );
});

export default AnimatedButton;