import React from "react";
import { Container, Grid2, Typography, TextField, Button } from "@mui/material";
import { signin } from "./ApiService";

function Login() {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.target);
    const username = data.get("username");
    const password = data.get("password");

    signin({ username: username, password: password });
  };

  return (
    // 로그인 컴포넌트
    <Container comporent="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid2 container spacing={2}>
        <Grid2 xs={12}>
          <Typography comporent="h1" variant="h5">
            로그인
          </Typography>
        </Grid2>
      </Grid2>

      <form noValidate onSubmit={handleSubmit}>
        {" "}
        {/* submit 버튼을 누르면 handleSubmit 실행*/}
        <Grid2 container spacing={2}>
          <Grid2 xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="username"
              label="아이디"
              name="username"
              autoComplete="username"
            />
          </Grid2>
          <Grid2 xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="password"
              label="비밀번호"
              type="password"
              name="password"
              autoComplete="current-password"
            />
          </Grid2>
          <Grid2>
            <Button type="submit" fullWidth variant="contained" color="primary">
              로그인
            </Button>
          </Grid2>
        </Grid2>
      </form>
    </Container>
  );
}

export default Login;
