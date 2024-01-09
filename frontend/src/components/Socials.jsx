import './styles/Socials.scss';
import { Icon } from "@iconify/react";

const Socials = () => {
  return (
    <div className='Socials'>
      <a href="." className='facebook'>
        <Icon icon="ri:facebook-fill" />
      </a>
      <a href="." className='twitter'>
        <Icon icon="ri:twitter-fill" />
      </a>
      <a href="." className='instagram'>
        <Icon icon="ri:instagram-fill" />
      </a>
      <a href="." className='tiktok'>
        <Icon icon="ic:baseline-tiktok" />
      </a>
    </div>
  );
}

export default Socials;