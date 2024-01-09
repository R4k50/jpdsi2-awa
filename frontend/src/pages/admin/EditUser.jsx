import './styles/EditUser.scss';
import { useParams } from 'react-router-dom'
import Form from '../../components/Form';
import Input from '../../components/Input';
import useForm from '../../hooks/useForm';
import useFetch from '../../hooks/useFetch';
import usePatch from '../../hooks/usePatch';
import ButtonAction from '../../components/ButtonAction';
import usePost from '../../hooks/usePost';

const EditUser = () => {
  const { id } = useParams();
  const { data: oldUser, isLoading, errors } = useFetch(`/user/${id}`);
  const { post: addRole } = usePost(`/addRole/${id}`);
  const { post: removeRole } = usePost(`/removeRole/${id}`);
  const { data: user, handleChange } = useForm();
  const { patchById, errors: patchErrors } = usePatch('/user');

  const isUserAdmin = oldUser?.roles?.includes("ROLE_ADMIN");
  const isUserDelivery = oldUser?.roles?.includes("ROLE_DELIVERY");

  const handleSubmit = async (e) => {
    e.preventDefault();
    await patchById(user, id, '/admin/users');
  }

  const handleAddRole = (role) => {
    addRole({ role });
  }

  const handleRemoveRole = (role) => {
    removeRole({ role });
  }


  return (
    <div className="EditUser">
      <Form
        title={`UPDATE USER ${id}`}
        accept="update"
        deny='back to users'
        denyredirect="/admin/users"
        onSubmit={handleSubmit}
        errors={patchErrors}
        isloading={isLoading ? 1 : 0}
      >
        <Input
          label='e-mail'
          name='email'
          value={oldUser?.email}
          onChange={handleChange}
        />
        <Input
          label='name'
          name='name'
          value={oldUser?.name}
          onChange={handleChange}
        />
        <Input
          label='surname'
          name='surname'
          value={oldUser?.surname}
          onChange={handleChange}
        />

        <p>Manage roles</p>
        {!isUserAdmin && <ButtonAction
          onClick={() => handleAddRole('ROLE_ADMIN')}
        >
          add to admins
        </ButtonAction>}
        {isUserAdmin && <ButtonAction
          onClick={() => handleRemoveRole('ROLE_ADMIN')}
          appearance='alt'
        >
          remove from admins
        </ButtonAction>}
        {!isUserDelivery && <ButtonAction
          onClick={() => handleAddRole('ROLE_DELIVERY')}
        >
          add to delivery
        </ButtonAction>}
        {isUserDelivery && <ButtonAction
          onClick={() => handleRemoveRole('ROLE_DELIVERY')}
          appearance='alt'
        >
          remove from delivery
        </ButtonAction>}
      </Form>
    </div>
  );
}

export default EditUser;