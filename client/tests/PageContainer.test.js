import React from 'react';
import { mount } from 'enzyme';
import ViewContainer from '../src/components/PageContainer';

describe('PageContainer', () => {
  let wrapper;

  describe('with defaultProps', () => {
    beforeEach(() => {
      wrapper = mount(<ViewContainer><div>hello world</div></ViewContainer>);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should renders', () => {
      expect(wrapper.find('div').length).toBeGreaterThan(0);
    });

    it('size prop should be defined', () => {
      expect(wrapper.props().size).toBeDefined();
    });

    it('children prop should be defined', () => {
      expect(wrapper.props().children).toBeDefined();
    });
  });
  describe('witout defaultProps', () => {
    beforeEach(() => {
      wrapper = mount(<ViewContainer size="small"><div>hello world</div></ViewContainer>);
    });

    it("size prop should be set to 'small'", () => {
      expect(wrapper.props().size).toBe('small');
    });
  });
});
