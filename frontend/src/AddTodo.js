import React, { useRef, useState } from "react";

import { Button, Grid2, TextField } from "@mui/material";

const AddTodo = (props) => {
  //사용자 입력 저장 오브젝트
  const [item, setItem] = useState({ title: "" });
  const inputRef = useRef(null);

  const onInputChange = (e) => {
    setItem({ title: e.target.value });
  };

  const addItem = props.addItem; //props로 addItem 함수 받기!

  const onButtonClick = (e) => {
    addItem(item);
    setItem({ title: "" });
    inputRef.current.focus();
  };

  const enterKeyEventHandle = (e) => {
    if (e.key === "Enter") {
      onButtonClick(e); //이벤트 객체 전달
    }
  };

  return (
    <Grid2 container sx={{ marginTop: 5 }}>
      <Grid2 xs={11} md={11} sx={{ paddingRight: 1 }}>
        <TextField
          placeholder="Add Todo here"
          fullWidth
          onChange={onInputChange}
          onKeyDown={enterKeyEventHandle}
          value={item.title}
          ref={inputRef}
        />
      </Grid2>
      <Grid2 xs={1} md={1}>
        <Button
          fullWidth
          sx={{ height: "100%" }}
          color="secondary"
          variant="outlined"
          onClick={onButtonClick}
        >
          +
        </Button>
      </Grid2>
    </Grid2>
  );
};

export default AddTodo;
