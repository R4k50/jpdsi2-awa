import './styles/FloatingMenu.scss';
import { animated, useTransition } from 'react-spring';
import Socials from './Socials';

const FloatingMenu = ({children, visibility}) => {
  const transitions = useTransition(visibility, {
    from: { translateY: '-100%' },
    enter: { translateY: '0%' },
    leave: { translateY: '-100%' },
    config: {
      duration: 300
    }
  })

  return transitions((style, item) => (
    item && <animated.nav style={style}>
      <div className='FloatingMenu webnav'>
        {children}
        <Socials />
      </div>
    </animated.nav>
  ))
}

export default FloatingMenu;