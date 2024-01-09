import './styles/FloatingNavbar.scss';
import StaticNavbar from "./StaticNavbar";
import { animated, useSpring } from 'react-spring';

const FloatingNavbar = ({visibility}) => {

  const animation = useSpring({
    from: { translateY: '-100%' },
    to: { translateY: visibility ? '0%' : '-100%' },
    config: {
      duration: 100
    }
  });

  return (
    <animated.div style={animation} className="FloatingNavbar">
      <StaticNavbar />
    </animated.div>
  );
}

export default FloatingNavbar;