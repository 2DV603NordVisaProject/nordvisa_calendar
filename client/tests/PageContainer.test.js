import React from 'react';
import { shallow } from 'enzyme';
import ViewContainer from '../src/components/PageContainer';

describe('ViewContainer', () => {
  let wrapper;

  describe('with defaultProps', () => {
    beforeEach(() => {
      wrapper = shallow(<ViewContainer><div>hello world</div></ViewContainer>);
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
      wrapper = shallow(<ViewContainer size="small"><div>hello world</div></ViewContainer>);
    });

    it("size prop should be set to 'small'", () => {
      expect(wrapper.props().size).toBe('small');
    });
  });
});
