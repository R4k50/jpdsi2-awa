import './styles/StaticNavbar.scss';
import { useRef, useState, useEffect, forwardRef } from 'react';
import { Link, NavLink } from 'react-router-dom';
import Hamburger from './Hamburger';
import FloatingMenu from './FloatingMenu';
import ButtonAction from './ButtonAction';
import useAuthContext from '../hooks/useAuthContext';
import useLogout from '../hooks/useLogout';


const StaticNavbar = forwardRef((props, ref) => {
  const { userData } = useAuthContext();
  const { logout, isLoading } = useLogout();
  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const btnRef = useRef(null);

  const handleClick = () => {
    setIsMenuVisible(prev => !prev);
  }

  const handleLogout = () => {
    if (userData)
      logout();
  }

  useEffect(() => {
    const handleClickOutside = e => {
        if (btnRef.current && !btnRef.current.contains(e.target))
          setIsMenuVisible(() => false);
    }

    document.addEventListener('click', handleClickOutside);
    return () => document.removeEventListener('click', handleClickOutside);
  }, [btnRef]);


  const Links = () => <>
    <div className="pages">
      <NavLink to="/" className='link'>home</NavLink>
      <NavLink to="/menu" className='link'>menu</NavLink>
      { userData && <NavLink to="/cart" className='link'>cart</NavLink> }
      { userData && <NavLink to="/orders" className='link'>orders</NavLink> }
      { userData?.roles?.includes("ROLE_DELIVERY") == true && <NavLink to="/delivery" className='link'>delivery</NavLink> }
      { userData?.roles?.includes("ROLE_ADMIN") == true && <NavLink to="/admin/dashboard" className='link'>admin</NavLink> }
    </div>
    <div className="auth">
      {!userData && <ButtonAction
        to="/register"
        appearance='alt'
        onClick={() => setIsMenuVisible(() => false)}
      >sign up</ButtonAction>}
      {userData
        ? <ButtonAction to="/" onClick={handleLogout} disabled={isLoading}>log out</ButtonAction>
        : <ButtonAction to="/login" onClick={() => setIsMenuVisible(() => false)}>log in</ButtonAction>
      }
    </div>
  </>

  return (
    <>
      <header {...props} ref={ref} className="Navbar webnav">
        <nav>
          <Link to="/" className='logo'><span>P</span>IZZA</Link>
          <Links/>
        </nav>
        <Hamburger
          onClick={handleClick}
          ref={btnRef}
          menuVisibility={isMenuVisible}
          className='menu'
        />
      </header>
      <FloatingMenu visibility={isMenuVisible}>
        <Links />
      </FloatingMenu>
    </>
  );
});

export default StaticNavbar;