package com.sobercoding.loopauth;

import com.sobercoding.loopauth.abac.policy.PolicyWrapper;
import com.sobercoding.model.Action;
import com.sobercoding.model.Contextual;
import com.sobercoding.model.ResObject;
import com.sobercoding.model.Subject;

/**
 * 普通账号访问政策
 * @author Sober
 */
public class AccountPolicy extends PolicyWrapper<Subject, Action, ResObject, Contextual> {
}
