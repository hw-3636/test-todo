import React, { useState } from "react";
import {
  ListItem,
  ListItemText,
  InputBase,
  Checkbox,
  IconButton,
} from "@mui/material";
import { DeleteForeverOutlined } from "@mui/icons-material";

const TodoV3 = (props) => {
  const [item, setItem] = useState(props.item);
  const [readOnly, setReadOnly] = useState(true);

  const deleteItem = props.deleteItem;
  const editItem = props.editItem;

  const handleCheckBoxChange = (e) => {
    item.done = e.target.checked;
    editItem(item);
  };

  const editEventHandler = (e) => {
    setItem({ ...item, titlE: e.target.value });
  };

  const turnOffReadOnly = () => {
    setReadOnly(false);
  };

  const turnOnReadOnly = (e) => {
    if (e.key === "Enter" && readOnly === false) {
      setReadOnly(true);
      editItem(item);
    }
  };

  return (
    <ListItem>
      <Checkbox checked={item.done} onChange={handleCheckBoxChange} />
      <ListItemText>
        <InputBase
          inputProps={{ "aria-label": "naked", readOnly: readOnly }}
          type="text"
          id={item.id.toString()}
          name={item.id.toString()}
          value={item.title}
          multiline={true}
          fullWidth={true}
          onChange={editEventHandler}
          onKeyDown={turnOnReadOnly}
          onClick={turnOffReadOnly}
        />
      </ListItemText>
      <IconButton aria-label="Delete Todo" onClick={() => deleteItem(item)}>
        <DeleteForeverOutlined />
      </IconButton>
    </ListItem>
  );
};

export default TodoV3;
