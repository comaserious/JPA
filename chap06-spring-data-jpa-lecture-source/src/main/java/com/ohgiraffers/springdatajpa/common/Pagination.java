package com.ohgiraffers.springdatajpa.common;

import org.springframework.data.domain.Page;

public class Pagination {

    public static PagingButton getPagingButtonInfo(Page page){

        /*현재 페이지에대한 정보 */
        int currentPage = page.getNumber() + 1 ;
        // +1 하는 이유는 인덱스번호 체계이기 때문

        /*페이지 버튼의 기본 갯수*/
        int defaultButtonCount = 10;



        /* 현재 시작 페이지를 계산 */
        int startPage = (int)(Math.ceil((double) currentPage / defaultButtonCount ) -1) * defaultButtonCount + 1;

        /* 끝 페이지 계산 */
        int endPage = startPage + defaultButtonCount -1;

        /* 실제 총 페이지가 endPage 보다 작으면 endPage 를 총 페이지로 */
        if(page.getTotalPages() < endPage) {
            endPage = page.getTotalPages();
        }
        /*페이지가 0  즉, 전체 페이지가 1장 일 경우*/
        if(page.getTotalPages() == 0 && endPage==0){
            endPage = startPage;
        }
        System.out.println("startPage = " + startPage);
        System.out.println("endPage = " + endPage);
        /*계산을 한 정보들을 반환*/
        return new PagingButton(currentPage,startPage,endPage);
    }

}
