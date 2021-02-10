#!/usr/bin/env bash

# 쉬고 있는 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음
function find_idle_profile()
{
    # 현재 nginx가 바라보고 있는 스프링부트가 정상적으로 수행 중인지 확인
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함) real2를 현재 profile로 사용
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      # IDLE_PROFILE: nginx와 연결되지 않은 profile
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    # bash는 값을 리턴하는 기능이 없음 => 제일 마지막줄에 echo로 결과 출력 후, 클라이언트에서 그 값을 잡아 사용 (중간에 echo 사용X)
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}