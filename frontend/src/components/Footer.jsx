import './styles/Footer.scss';
import { NavLink } from 'react-router-dom';
import { Icon } from '@iconify/react';
import Socials from './Socials';
import useAuthContext from '../hooks/useAuthContext';

const Footer = () => {
  const { userData } = useAuthContext();

  return (
    <footer>
      <div className="main">
        <h2 className='logo'><span>P</span>IZZA</h2>
        <p>We don't just make pizza, we make memories.</p>
      </div>
      <div className="help">
        <h3>HELP</h3>
        <nav>
          <NavLink to='/'>home</NavLink>
          <NavLink to='/menu'>menu</NavLink>
          {userData ? <>
            <NavLink to='/cart'>cart</NavLink>
            <NavLink to='/orders'>orders</NavLink>
          </> : <>
            <NavLink to='/register'>sign up</NavLink>
            <NavLink to='/login'>log in</NavLink>
          </>}
        </nav>
      </div>
      <div className="contact">
        <h3>CONTACT</h3>
        <p className="tel info"><Icon icon="icon-park-solid:phone-telephone"/>000-000-000</p>
        <p className="mail info"><Icon icon="material-symbols:mail-outline-rounded"/>pizza@example.com</p>
      </div>
      <div className="socials">
        <h3>SOCIAL MEDIA</h3>
        <Socials />
      </div>
    </footer>
  );
}

export default Footer;