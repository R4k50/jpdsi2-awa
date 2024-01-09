import Input from './Input';
import ButtonAction from './ButtonAction';

const SearchInput = ({names, filter, filterValue, setFilter, setfilterValue, order, orderValue, search, clearSearch}) => {
  return (
    <div className="Search">
      <select onChange={setFilter}>
        {names.map(name => (
          <option value={name} key={name}>{name}</option>
        ))}
      </select>
      <Input
        label='filter'
        name={filter}
        value={filterValue}
        onChange={setfilterValue}
      />
      <p>order: {order} | {orderValue ? 'ASC' : 'DESC'}</p>
      <ButtonAction onClick={search}>Search</ButtonAction>
      <ButtonAction onClick={clearSearch}>Clear</ButtonAction>
    </div>
  );
}

export default SearchInput;