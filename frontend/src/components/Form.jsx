import './styles/Form.scss';
import { forwardRef, useEffect, useState } from 'react';
import { animated, useTransition } from 'react-spring';
import ButtonAction from './ButtonAction';
import pizza2 from '../images/Pizza2.png';
import pizza3 from '../images/Pizza3.png';
import pizza4 from '../images/Pizza4.png';
import Errors from './Errors';

const Form = forwardRef((props, ref) => {
  const {
    children,
    onSubmit,
    title,
    accept,
    deny,
    denyredirect,
    errors,
    isloading
  } = props;

  const [formErrors, setFormErrors] = useState(errors);

  useEffect(() => {
    setFormErrors(() => errors);
  }, [errors])

  return (
    <animated.div {...props} className={`Form ${props.className || ''}`} onSubmit={null} title={null} ref={ref}>
      <div className="container">
        <h2>{title || 'Form'}</h2>
        <form onSubmit={onSubmit}>
          <div className="inputs">
            {children}
          </div>
          <div className="actions">
            <ButtonAction type='submit' disabled={isloading}>{accept || "accept"}</ButtonAction>
            <ButtonAction to={denyredirect || '/'} appearance='alt'>{deny || "deny"}</ButtonAction>
          </div>
          <Errors errors={formErrors}/>
        </form>
      </div>
      <img className='pizza' src={pizza2} alt="pizza" />
      <img className='pizza' src={pizza3} alt="pizza" />
      <img className='pizza' src={pizza4} alt="pizza" />
    </animated.div>
  );
});

export default Form;