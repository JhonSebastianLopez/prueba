package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresControlador implements ErrorController {

    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView renderErrorPage(HttpServletRequest httpServletRequest) {

        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = 500;
        if (status != null) {
        statusCode = Integer.parseInt(status.toString());
        }

        String mensaje = switch (statusCode) {
            case 400 -> "El recurso solicitado no existe";
            case 401 -> "No se encuentra autorizado";
            case 403 -> "No tiene permiso para acceder a este recurso";
            case 404 -> "El recurso solicitado no fue encontrado";
            case 500 -> "Ocurrió un error interno en el servidor";
            default -> "Error inesperado";
        };

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("codigo", statusCode);
        mav.addObject("mensaje", mensaje);
        return mav;
    }

}

// @Controller
// public class ErroresControlador implements ErrorController{

// @RequestMapping(value = "/error", method = { RequestMethod.GET,
// RequestMethod.POST })
// public ModelAndView renderErrorPage(HttpServletRequest httpServletRequest) {

// ModelAndView errorPage = new ModelAndView("error");

// String errorMsg = "";

// int httpErrorCode = getErrorCode(httpServletRequest);

// switch (httpErrorCode) {
// case 400:
// errorMsg = "El recurso solicitado no existe";
// break;
// case 401:
// errorMsg = "No se encuentra autorizado";
// break;
// case 403:
// errorMsg = "No tiene permiso para acceder a este recurso";
// break;
// case 404:
// errorMsg = "El recurso solicitado no fue encontrado";
// break;
// case 500:
// errorMsg = "Ocurrio un error interno en el servidor";
// break;
// default:
// errorMsg = "Error inesperado";
// break;
// }

// errorPage.addObject("codigo", httpErrorCode);
// errorPage.addObject("mensaje", errorMsg);

// return errorPage;

// }

// private int getErrorCode(HttpServletRequest httpServletRequest) {
// return (int)
// httpServletRequest.getAttribute("jakarta.servlet.error.status_code");
// }

// public String getErrorPath() {
// return "/error";
// }

// }
