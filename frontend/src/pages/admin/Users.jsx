import './styles/Users.scss';
import useForm from '../../hooks/useForm';
import usePost from '../../hooks/usePost';
import Form from '../../components/Form';
import Input from '../../components/Input';
import Filter from '../../components/Filter';
import { useState } from 'react';

const Users = () => {
  const { post, isLoading: isLoadingNew, errors: errorsNew } = usePost('/user');
  const { data: user, handleChange } = useForm();
  const [isSubmitted, setIsSubmitted] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await post(user);

    setIsSubmitted(prev => !prev);
  }

  return (
    <div className="Users">
      <Form
        title={'CREATE A NEW USER'}
        accept="create"
        deny="return to dashboard"
        denyredirect='/admin/dashboard'
        errors={errorsNew}
        isloading={isLoadingNew ? 1 : 0}
        onSubmit={handleSubmit}
      >
        <Input
          label='e-mail'
          name='email'
          onChange={handleChange}
        />
        <Input
          label='name'
          name='name'
          onChange={handleChange}
        />
        <Input
          label='surname'
          name='surname'
          onChange={handleChange}
        />
        <Input
          label='password'
          name='password'
          type='password'
          onChange={handleChange}
        />
      </Form>
      <Filter
        model='user'
        modelPlural='users'
        disabledColumns={['token']}
        refreshing={isSubmitted}
      />
    </div>
  );
}

export default Users;