import './styles/Navbar.scss';
import { useEffect, useState } from 'react';
import useObserver from '../hooks/useObserver';

import StaticNavbar from './StaticNavbar';
import FloatingNavbar from './FloatingNavbar';


const Navbar = () => {
  const [isSmallScreen, setIsSmallScreen] = useState(false);
  const { ref, isVisible } = useObserver({
    root: null,
    rootMargin: '50%',
    threshold: 1
  });

  useEffect(() => {
    setIsSmallScreen(window.screen.width < 1024)
  }, [setIsSmallScreen]);

  return (
    <>
      {!isSmallScreen && <StaticNavbar ref={ref} />}
      <FloatingNavbar visibility={(isSmallScreen || !isVisible)} />
    </>
  );
}

export default Navbar;