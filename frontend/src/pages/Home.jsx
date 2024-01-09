import './styles/Home.scss';
import { Icon } from '@iconify/react';
import ButtonAction from '../components/ButtonAction';
import Socials from '../components/Socials';
import pizza1 from '../images/Pizza1.png';
import pizza2 from '../images/Pizza2.png';
import pizza3 from '../images/Pizza3.png';
import pizza4 from '../images/Pizza4.png';
import useAuthContext from '../hooks/useAuthContext';
import { animated, useTransition } from 'react-spring';

const Home = () => {
  const { userData } = useAuthContext();

  const serviceProcessItems = [
    {
      icon: "material-symbols:app-shortcut-rounded",
      title: "Place an order"
    },
    {
      icon: "ph:package-fill",
      title: "Order preparation"
    },
    {
      icon: "carbon:delivery",
      title: "Delivery"
    },
    {
      icon: "fluent:food-pizza-24-regular",
      title: "Order received"
    },
  ];

  const images = [
    {
      src: pizza1,
      className: 'pizza'
    },
    {
      src: pizza2,
      className: 'pizza secondary'
    }
  ]

  const transition = useTransition(images, {
    from: {
      opacity: 1,
      translateX: '35rem',
      rotate: '180deg'
    },
    enter: {
      opacity: 1,
      translateX: '0rem',
      rotate: '0deg'
    },
    trail: 200,
    delay: 100,
    config: {
      duration: 500
    }
  });


  return (
    <>
      <div className="gradient"></div>
      <div className="Home">
        <section className="hero">
          <h2>CRAFTING <span>QUALITY</span> AND <span>FLAVOR</span>, SLICE BY SLICE</h2>
          <p>We believe that quality ingredients and exceptional flavor go hand in hand. That's why every slice of our pizza is made with care and attention to detail, ensuring a taste experience like no other.</p>
          <Icon icon="ph:arrow-arc-left-light" />
          <div className="actions">
            <ButtonAction to='/menu'>place an order<Icon icon="material-symbols:arrow-forward-ios-rounded" /></ButtonAction>
            <ButtonAction to={userData ? '/orders' : '/login'} appearance='alt'>track your order<Icon icon="ph:magnifying-glass-bold" /></ButtonAction>
          </div>
          {transition((style, image, key) => (
            image && <animated.img style={style} src={image.src} className={image.className} key={key} alt="pizza" />
          ))}
          <Socials />
        </section>
        <section className="service">
          <div className="container">
            <h2>SERVICE PROCESS</h2>
            <div className="process">
              {serviceProcessItems.map(({icon, title}, key) => (
                <div key={key}>
                  <Icon className="icon" icon={icon} />
                  <p>{title}</p>
                </div>
              ))}
            </div>
          </div>
          <div className="arrows">
            <Icon icon="ph:arrow-arc-left-light" />
            <Icon icon="ph:arrow-arc-left-light" />
            <Icon icon="ph:arrow-arc-left-light" />
          </div>
          <div className="menu">
            <h3>CHECK OUT OUR MENU</h3>
            <ButtonAction to='/menu' appearance='alt'>see more<Icon icon="material-symbols:arrow-forward-ios-rounded" /></ButtonAction>
          </div>
          <img src={pizza3} className='pizza' alt="pizza" />
          <img src={pizza4} className='pizza secondary' alt="pizza" />
          <Icon icon="ph:arrow-arc-left-light" />
        </section>
        <section className="happymonday">
          <div className="container">
          <div className="days">
            {[...Array(7).keys()].map((el) => (
              <Icon key={el} icon="material-symbols:calendar-today-rounded" />
            ))}
          </div>
            <h2>HAPPY MONDAY</h2>
            <h3>Start your week right with <span>10% off</span> every monday :)</h3>
            <p>Looking for a reason to love Mondays? Our weekly 10% discount is here to make your day brighter! Every Monday, get 10% off your entire order. From classic pies to creative toppings, we've got you covered.</p>
          </div>
          <div className="icon">
            <Icon icon="noto:pizza" />
          </div>
        </section>
      </div>
    </>
  );
}

export default Home;