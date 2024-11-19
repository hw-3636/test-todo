import { Typography, Box } from "@mui/material";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import Login from "./Login"; //오류 무시해

//React 컴포넌트, 앱의 라우팅과 하위 구조를 설정하는 역할을 수행
//클라이언트 사이드 라우팅을 담당
//브라우저의 URL 경로에 따라 적절한 React 컴포넌트 렌더링하는 역할!
//---- 스프링의 Front View Controller 와 비슷한 개념으로 일단은 알고 있기.

//기본 저작권 문구
function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © "}
      Company Name, {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

function AppRouter() {
  return (
    <div>
      <BrowserRouter
        future={{ v7_startTransition: true, v7_relativeSplatPath: true }}
      >
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="login" element={<Login />} />
        </Routes>
      </BrowserRouter>
      <Box mt={5}>
        <Copyright />
      </Box>
    </div>
  );
}

export default AppRouter;
