import './styles/ButtonAction.scss';
import { Link } from 'react-router-dom';
import AnimatedButton from './wrappers/AnimatedButton';

const ButtonAction = (props) => {
  const {
    onClick,
    to,
    appearance,
    children
  } = props;

  const handleClick = e => {
    if (onClick)
      onClick();
  }

  return (
    <AnimatedButton
      {...props}
      className={`ButtonAction ${appearance === 'alt' ? 'Alt' : ''}`}
      onClick={handleClick}
    >
      {to && <Link to={to} className='content'>{children}</Link>}
      {!to && <span className='content'>{children}</span>}
    </AnimatedButton>
  );
}

export default ButtonAction;