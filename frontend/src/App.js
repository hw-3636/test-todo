import "./App.css";
import TodoV3 from "./TodoV3";
import React, { useState, useEffect } from "react";
import { Container, List, Paper } from "@mui/material";
import AddTodo from "./AddTodo";
import { call } from "./ApiService";

function App() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    call("/todo", "GET", null).then((response) => setItems(response.data));
  }, []);

  const addItem = (item) => {
    call("/todo", "POST", item).then((response) => setItems(response.data));
  };

  const deleteItem = (item) => {
    call("/todo", "DELETE", item).then((response) => setItems(response.data));
  };

  const editItem = (item) => {
    call("/todo", "PUT", item).then((response) => setItems(response.data));
  };

  let todoItems = items.length > 0 && (
    <Paper style={{ margin: "16px 0 0 0" }}>
      <List>
        {items.map((item) => (
          <TodoV3
            item={item}
            key={item.id}
            deleteItem={deleteItem}
            editItem={editItem}
          />
        ))}
      </List>
    </Paper>
  );

  items.map((item) => <TodoV3 item={item} key={item.id} />);

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo addItem={addItem} />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;

// const addItem = (item) => {
//   item.id = "ID-" + items.length;
//   item.done = false; //done 초기화, 처음 등록 시 체크되어있으면 안 됨

//   setItems([...items, item]);
// };

// const deleteItem = (targetItem) => {
//   setItems((prevItems) => {
//     return prevItems.filter((item) => item.id !== targetItem.id);
//   });
// };
