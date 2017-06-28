import React from 'react';
import { shallow } from 'enzyme';
import GeneratedOutput from '../src/components/GeneratedOutput';
import en from '../src/i18n/en';

describe('GeneratedOutput', () => {
  let wrapper;
  const context = { language: en };
  const headCode = 'head code';
  const bodyCode = 'body code';

  describe('is not generated', () => {
    beforeEach(() => {
      wrapper = shallow(<GeneratedOutput
        headCode={headCode}
        bodyCode={bodyCode}
        isGenerated={false}
      />, { context });
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('should not render', () => {
      expect(wrapper.find('textarea').length).toBe(0);
    });
  });

  describe('is generated', () => {
    beforeEach(() => {
      wrapper = shallow(<GeneratedOutput
        headCode={headCode}
        bodyCode={bodyCode}
        isGenerated
      />, { context });
    });

    it('should render', () => {
      expect(wrapper.find('textarea').length).toBe(2);
    });

    it('should display headCode', () => {
      const input = wrapper.find('textarea').first();
      expect(input.props().defaultValue).toBe(headCode);
    });

    it('should display bodyCode', () => {
      const input = wrapper.find('textarea').at(1);
      expect(input.props().defaultValue).toBe(bodyCode);
    });
  });
});
