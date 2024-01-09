import { createContext, useReducer, useEffect } from 'react';


const login = (payload) => {
  const newState = {
    userData: payload
  };

  localStorage.setItem('user', JSON.stringify(newState));
  return newState;
}

const register = (payload) => {
  const newState = {
    userData: payload
  };

  localStorage.setItem('user', JSON.stringify(newState));
  return newState;
}

const restore = (payload) => {
  return payload;
}

const logout = () => {
  localStorage.removeItem('user');
  return { userData: null }
}


export const AuthContext = createContext();

export const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      return login(action.payload);

    case 'REGISTER':
      return register(action.payload);

    case 'LOGOUT':
      return logout();

    case 'RESTORE':
      return restore(action.payload);

    default:
      return state;
  }
}

export const AuthContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, {
    userData: null
  });

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user)
      dispatch({ type: 'RESTORE', payload: user });
  }, []);

  return (
    <AuthContext.Provider value={{ ...state, dispatch }}>
      { children }
    </AuthContext.Provider>
  );
}