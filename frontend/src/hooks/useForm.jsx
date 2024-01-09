import { useState } from "react";

export default function useForm() {
  const [data, setData] = useState({});

  const handleChange = ({ target }) => {
    if (target.type == 'checkbox')
      target.value = target.checked ? 1 : 0;

    if (target.value && target.value != '') {
      setData(prev => ({
        ...prev,
        [target.name]: target.value
      }));
    }
  }

  return { data, handleChange };
}