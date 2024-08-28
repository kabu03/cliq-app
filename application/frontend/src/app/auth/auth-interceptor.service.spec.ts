import { authInterceptorFn } from './auth-interceptor.service';
import { HttpRequest, HttpHandler } from '@angular/common/http';
import { of } from 'rxjs';

describe('authInterceptorFn', () => {
  it('should add an Authorization header if token is present', () => {
    // Mock token in localStorage
    spyOn(localStorage, 'getItem').and.returnValue('mockToken');

    // Create a mock HttpRequest and HttpHandler
    const mockRequest = new HttpRequest('GET', '/test');
    const mockHandler: HttpHandler = {
      handle: jasmine.createSpy('handle').and.returnValue(of(null))
    };

    // Call the interceptor function
    authInterceptorFn(mockRequest, mockHandler.handle);

    // Expect the cloned request to have the Authorization header
    expect(mockHandler.handle).toHaveBeenCalledWith(
      jasmine.objectContaining({
        headers: jasmine.objectContaining({
          Authorization: 'Bearer mockToken'
        })
      })
    );
  });

  it('should not add an Authorization header if token is absent', () => {
    // Mock token absence in localStorage
    spyOn(localStorage, 'getItem').and.returnValue(null);

    // Create a mock HttpRequest and HttpHandler
    const mockRequest = new HttpRequest('GET', '/test');
    const mockHandler: HttpHandler = {
      handle: jasmine.createSpy('handle').and.returnValue(of(null))
    };

    // Call the interceptor function
    authInterceptorFn(mockRequest, mockHandler.handle);

    // Expect the original request without the Authorization header
    expect(mockHandler.handle).toHaveBeenCalledWith(mockRequest);
  });
});
