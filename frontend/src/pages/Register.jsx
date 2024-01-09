import './styles/Register.scss';
import Form from '../components/Form';
import Input from '../components/Input';
import useRegister from '../hooks/useRegister';
import useForm from '../hooks/useForm';
import { useTransition } from 'react-spring';

const Register = () => {
  const { data: user, handleChange } = useForm();
  const { register, errors, isLoading } = useRegister();

  const handleSubmit = async e => {
    e.preventDefault();
    await register(user);
  }

  const transition = useTransition(true, {
    from: {
      opacity: 0,
      y: -50
    },
    enter: {
      opacity: 1,
      y: 0
    }
  });

  return (
    <div className="Signup">
      {transition((style, item) => (
        item && <Form
          title='CREATE A NEW ACCOUNT'
          accept="register"
          deny='log in'
          denyredirect="/login"
          onSubmit={handleSubmit}
          errors={errors}
          isloading={isLoading ? 1 : 0}
          style={style}
        >
          <Input onChange={handleChange} errors={errors} name='name' label='name'/>
          <Input onChange={handleChange} errors={errors} name='surname' label='surname' />
          <Input onChange={handleChange} errors={errors} name='email' label='e-mail' />
          <Input onChange={handleChange} errors={errors} name='password' label='password' type='password' />
          <Input onChange={handleChange} errors={errors} name='passwordConfirmation' label='confirm password' type='password' />
        </Form>
      ))}
    </div>
  );
}

export default Register;