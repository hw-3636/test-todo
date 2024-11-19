import { API_BASE_URL } from "./api-config";

export function call(api, method, request) {
  //헤더 정의
  let headers = new Headers({
    "Content-Type": "application/json",
  });

  //로컬 스토리지에서 ACCESS TOKEN 가져와서 헤더에 세팅
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.append("Authorization", "Bearer " + accessToken);
  }

  //헤더 추가
  let options = {
    headers: headers,
    url: API_BASE_URL + api,
    method: method,
  };

  if (request) {
    //GET method
    options.body = JSON.stringify(request);
  }

  return fetch(options.url, options)
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 403) {
        //403 에러가 뜰 때
        window.location.href = "/login"; // '/login' 으로 리다이렉트할 것
      } else {
        new Error(response);
      }
    })
    .catch((error) => {
      console.error("http error: ", error);
    });
}

export function signin(userDTO) {
  return call("/auth/signin", "POST", userDTO).then((response) => {
    //alert("로그인 토큰: " + response.token);  -> 디버깅용, 로그인 토큰 잘 넘어오는지 확인 위함
    if (response.token) {
      //로컬 스토리지에 토큰 저장
      localStorage.setItem("ACCESS_TOKEN", response.token);
      // token 존재 시 Todo 화면으로 리다이렉트
      window.location.href = "/";
    }
  });
}

export function signout() {
  localStorage.setItem("ACCESS_TOKEN", null);
  window.location.href = "/login";
}
