import './styles/Login.scss';
import Form from '../components/Form';
import Input from '../components/Input';
import useLogin from '../hooks/useLogin';
import useForm from '../hooks/useForm';
import { useTransition } from 'react-spring';

const Login = () => {
  const { data: credentials, handleChange } = useForm();
  const { login, errors, isLoading } = useLogin();

  const handleSubmit = async e => {
    e.preventDefault();
    await login(credentials);
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
    <div className="Login">
      {transition((style, item) => (
        item && <Form
          title='LOGIN TO YOUR ACCOUNT'
          accept="log in"
          deny='sign up'
          denyredirect="/signup"
          onSubmit={handleSubmit}
          errors={errors}
          isloading={isLoading ? 1 : 0}
          style={style}
        >
          <Input
            onChange={handleChange}
            label='e-mail'
            name='email'
          />
          <Input
            onChange={handleChange}
            label='password'
            type='password'
            name='password'
          />
        </Form>
      ))}
    </div>
  );
}

export default Login;